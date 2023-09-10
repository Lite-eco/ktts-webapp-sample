import { Collections } from './generate-routing-utils.mjs';
import fs from 'fs';

export const generateRouter = (file, resultRoutes, interfacesImports) => {
  if (resultRoutes.length !== 0) {
    const extractComponents = r => [
      r.component,
      ...(r.subRoutes ?? []).flatMap(s => extractComponents(s))
    ];
    const imports = new Set(resultRoutes.flatMap(r => extractComponents(r)));
    let source = '/** @jsxImportSource @emotion/react */\n';
    source += printImports(interfacesImports, imports);
    source += "\nimport { RouteObject } from 'react-router-dom';";
    source += '\n\n';
    source += 'export const router: RouteObject[] = [\n';
    resultRoutes.forEach(r => {
      source += printRoute(r);
    });
    source += ']';
    fs.writeFile(file, source, err => {
      if (err) {
        throw err;
      }
    });
  }
};

export const printImports = (interfacesImports, imports) => {
  const i = interfacesImports.filter(o => imports.has(o[0]));
  const g = Collections.groupBy(i, a => a[1]);
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
  let res = `{\nid: "${r.id}",\n`;
  if (r.path) {
    res += `path: "${r.path}",\n`;
  }
  res += `element: <${r.component} />,\n`;
  if (r.subRoutes) {
    res += 'children: [' + r.subRoutes.map(r => printRoute(r)).join('\n') + ']';
  }
  return res + '},';
};
