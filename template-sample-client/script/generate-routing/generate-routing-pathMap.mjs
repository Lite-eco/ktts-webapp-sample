import { notFoundPath } from './generate-routing-utils.mjs';
import fs from 'fs';

const extractPairs = routes =>
  routes.flatMap(r => [
    [r.name, r.fullPath],
    ...extractPairs(r.subRoutes ?? [])
  ]);

export const generatePathMap = (file, resultRoutes) => {
  if (resultRoutes.length !== 0) {
    let source = "import { dict } from '../utils/nominal-class';\n";
    source +=
      "import { ApplicationRoute } from './ApplicationRoute.generated';\n";
    source += '\n\n';
    source +=
      "export const routePathMap = dict<ApplicationRoute['name'], string>([\n";
    source += extractPairs(resultRoutes)
      // take off containers
      .filter(r => r[0])
      // take off Not Found
      .filter(r => r[1] !== notFoundPath)
      .map(r => `['${r[0]}', '/${r[1]}'],`)
      .join('\n');
    source += '])';
    fs.writeFile(file, source, err => {
      if (err) {
        throw err;
      }
    });
  }
};
