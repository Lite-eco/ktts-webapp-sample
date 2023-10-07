import { Collections } from './generate-routing-utils.mjs';
import fs from 'fs';

const sortByIdWithNotFoundLast = (r1, r2) => {
  if (r1.id === 'NotFound') {
    return 1;
  } else if (r2.id === 'NotFound') {
    return -1;
  } else {
    return r1.id.localeCompare(r2.id);
  }
};

export const generateRouter = (file, resultRoutes, interfacesImports) => {
  if (resultRoutes.length !== 0) {
    const extractComponents = r => [
      r.component,
      r.rootComponent,
      ...(r.subRoutes ?? []).flatMap(s => extractComponents(s))
    ];
    const imports = new Set(resultRoutes.flatMap(r => extractComponents(r)));
    let source = '/** @jsxImportSource @emotion/react */\n';
    source += printImports(interfacesImports, imports);
    source +=
      "\nimport { createBrowserRouter, RouteObject } from 'react-router-dom';";
    source += '\n\n';
    source += 'export const routes: RouteObject[] = [\n';
    resultRoutes
      .sort(sortByIdWithNotFoundLast)
      .forEach(r => (source += printRoute(r)));
    source += ']\n';
    source += 'export const router = createBrowserRouter(routes);';
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
    const path = `../../${k}`
      .replaceAll('/./', '/')
      .replaceAll('/../generated/', '/');
    return `import ${defaultImport} { ${imports} } from '${path}';`;
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
    res += 'children: [';
    if (r.rootComponent) {
      // TODO remove '_' by controlling '/' presence ?
      res += `{id: '${r.id}/_', path: '', element: <${r.rootComponent} />},`;
    }
    res += r.subRoutes
      .sort(sortByIdWithNotFoundLast)
      .map(r => printRoute(r))
      .join('\n');
    res += ']';
  }
  return res + '},';
};
