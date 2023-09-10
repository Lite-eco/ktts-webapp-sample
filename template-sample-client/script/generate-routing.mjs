import { generateInterfaces } from './generate-routing/generate-routing-interfaces.mjs';
import { parseFile } from './generate-routing/generate-routing-parser.mjs';
import { generatePathMap } from './generate-routing/generate-routing-pathMap.mjs';
import { generateRouter } from './generate-routing/generate-routing-router.mjs';

const dir = './src/routing/';
const { interfacesImports, resultRoutes } = parseFile(dir + 'routes-dsl.ts');
generateRouter(dir + 'router.generated.tsx', resultRoutes, interfacesImports);
generateInterfaces(
  dir + 'ApplicationRoute.generated.ts',
  resultRoutes,
  interfacesImports
);
generatePathMap(dir + 'routePathMap.generated.ts', resultRoutes);
