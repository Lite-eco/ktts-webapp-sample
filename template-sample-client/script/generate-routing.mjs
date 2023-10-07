import { generateInterfaces } from './generate-routing/generate-routing-interfaces.mjs';
import { parseFile } from './generate-routing/generate-routing-parser.mjs';
import { generatePathMap } from './generate-routing/generate-routing-pathMap.mjs';
import { generateRouter } from './generate-routing/generate-routing-router.mjs';
import fs from 'fs';

const rootDir = './src';
const generationDir = rootDir + '/generated/routing';
const { interfacesImports, resultRoutes } = parseFile(
  rootDir + '/routes-dsl.ts'
);
if (!fs.existsSync(generationDir)) {
  fs.mkdirSync(generationDir, { recursive: true });
}
generateRouter(
  generationDir + '/router.generated.tsx',
  resultRoutes,
  interfacesImports
);
generateInterfaces(
  generationDir + '/ApplicationPath.generated.ts',
  resultRoutes,
  interfacesImports
);
generatePathMap(generationDir + '/routerPathMap.generated.ts', resultRoutes);
