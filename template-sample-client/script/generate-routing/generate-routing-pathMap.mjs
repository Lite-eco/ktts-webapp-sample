import { compareByString, notFoundPath } from './generate-routing-utils.mjs';
import fs from 'fs';

const extractPairs = routes =>
  routes.flatMap(r => [
    [r.name, r.fullPath],
    ...extractPairs(r.subRoutes ?? [])
  ]);

export const generatePathMap = (file, resultRoutes) => {
  if (resultRoutes.length !== 0) {
    let source = "import { pairsToDict } from '../../utils/nominal-class';\n";
    source +=
      "import { ApplicationPath } from './ApplicationPath.generated';\n";
    source += '\n\n';
    source +=
      "export const routerPathMap = pairsToDict<ApplicationPath['name'], string>([\n";
    source += extractPairs(resultRoutes)
      // take off layouts
      .filter(r => r[0])
      // take off Not Found
      .filter(r => r[1] !== notFoundPath)
      .sort(compareByString(r => r[0]))
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
