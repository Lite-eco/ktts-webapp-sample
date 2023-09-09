const ts = require('typescript');
const fs = require('fs');

const printer = ts.createPrinter({ newLine: ts.NewLineKind.LineFeed });

// output
const interfacesImports = [];
const interfaces = [];
let resultRoutes = [];

const notFoundPath = '*';

// utils
const extractElement = (node, fieldName) =>
  node.members.find(m => m?.name?.escapedText === fieldName);

const parseElements = (elements, parentName, parentPath, parentParams) =>
  elements.map(e => {
    const name = extractElement(e, 'name').type.literal.text;
    const fullName = parentName ? `${parentName}/${name}` : name;
    console.log('[debug] parsing route ' + fullName);
    const path = extractElement(e, 'path').type.literal.text;
    const fullPath = parentPath ? parentPath + '/' + path : path;
    const component = extractElement(e, 'component').type.exprName.escapedText;
    const result = {
      name: fullName,
      path: path ? path : '/',
      fullPath: fullPath,
      component
    };
    const params = [
      ...(parentParams ?? []),
      ...(extractElement(e, 'params')?.type?.members ?? [])
    ];
    if (path !== notFoundPath) {
      interfaces.push([fullName, params]);
    }
    const subRoutes = extractElement(e, 'subRoutes');
    if (subRoutes) {
      result['subRoutes'] = parseElements(
        subRoutes.type.elements,
        fullName,
        fullPath,
        params
      );
    }
    return result;
  });

// read route-dsl.ts content
const node = ts.createSourceFile(
  'x.ts',
  fs.readFileSync('./src/routing/routes-dsl.ts', 'utf8'),
  ts.ScriptTarget.Latest
);

// parse route-dsl.ts content
node.statements.forEach(s => {
  // first, imports
  if ('importClause' in s) {
    // default import
    if (s.importClause.name) {
      interfacesImports.push([
        s.importClause.name.escapedText,
        s.moduleSpecifier.text,
        'default'
      ]);
    }
    // non-default imports
    (s.importClause.namedBindings?.elements ?? []).forEach(e => {
      interfacesImports.push([e.name.escapedText, s.moduleSpecifier.text]);
    });
    return undefined;
  }
  // parse routes
  if (s.name.escapedText === 'routesDsl') {
    resultRoutes = parseElements(s.type.elements);
  }
});

const extractComponents = r => [
  r.component,
  ...(r.subRoutes ?? []).flatMap(s => extractComponents(s))
];

const groupBy = (a, key) => {
  const map = {};
  a.forEach(item => {
    const k = key(item);
    const existing = map[k];
    const list = existing ? existing : [];
    if (!existing) {
      map[k] = list;
    }
    list.push(item);
  });
  return map;
};

const printImports = imports => {
  const i = interfacesImports.filter(o => imports.has(o[0]));
  const g = groupBy(i, a => a[1]);
  const r = Object.keys(g).map(k => {
    const defaultImports = g[k].filter(i => i[2] === 'default');
    const defaultImport =
      defaultImports.length === 1 ? `${defaultImports[0][0]},` : '';
    const imports = g[k]
      .filter(i => !i[2])
      .map(v => v[0])
      .join(', ');
    return `import ${defaultImport} { ${imports} } from '${k}';`;
  });
  return r.join('\n');
};

const printRoute = r => {
  let res = `{
    id: "${r.name}",
    path: "${r.path}",
    element: <${r.component} />,\n`;
  if (r.subRoutes) {
    res += 'children: [' + r.subRoutes.map(r => printRoute(r)).join('\n') + ']';
  }
  return res + '},';
};

// generate router
if (resultRoutes.length !== 0) {
  const imports = new Set(resultRoutes.flatMap(r => extractComponents(r)));
  let source = '/** @jsxImportSource @emotion/react */\n';
  source += printImports(imports);
  source += '\n\n';
  source += 'export const router = [\n';
  resultRoutes.forEach(r => {
    source += printRoute(r);
  });
  source += ']';
  fs.writeFile('./src/routing/router.generated.tsx', source, err => {
    if (err) {
      throw err;
    }
  });
}

const extractPairs = routes =>
  routes.flatMap(r => [
    `['${r.name}', '/${r.fullPath}'],`,
    ...extractPairs(r.subRoutes ?? [])
  ]);

// generate routePathMap
if (resultRoutes.length !== 0) {
  let source = "import { dict } from '../utils/nominal-class';\n";
  source +=
    "import { ApplicationRoute } from './ApplicationRoute.generated';\n";
  source += '\n\n';
  source +=
    "export const routePathMap = dict<ApplicationRoute['name'], string>([\n";
  source += extractPairs(
    resultRoutes
      // take off Not Found
      .filter(r => r.path !== notFoundPath)
  ).join('\n');
  source += '])';
  fs.writeFile('./src/routing/routePathMap.generated.ts', source, err => {
    if (err) {
      throw err;
    }
  });
}

// generate interfaces
if (interfaces.length !== 0) {
  const imports = new Set(
    interfaces
      .map(i => i[1])
      .flatMap(i => i.map(o => o.type.typeName.escapedText))
  );
  let source = printImports(imports);
  source += '\n\n';
  source += 'export type ApplicationRoute = ';
  source += interfaces
    .map(i => `| ${i[0].replaceAll('/', '')}Route`)
    .join('\n');
  source += '\n\n';
  source += interfaces
    .map(i => {
      let interf = i[1].length !== 0 ? 'export ' : '';
      interf += 'interface ';
      interf += i[0].replaceAll('/', '');
      interf += 'Route {\n';
      interf += "    name: '";
      interf += i[0];
      interf += "';\n    ";
      interf += i[1]
        .map(m => printer.printNode(ts.EmitHint.Unspecified, m))
        .join('\n    ');
      interf += '\n}\n';
      return interf;
    })
    .join('\n');
  fs.writeFile('./src/routing/ApplicationRoute.generated.ts', source, err => {
    if (err) {
      throw err;
    }
  });
}
