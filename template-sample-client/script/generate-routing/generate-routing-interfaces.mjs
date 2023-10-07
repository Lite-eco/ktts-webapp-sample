import { printImports } from './generate-routing-router.mjs';
import {
  compareByString,
  typescriptPrinter
} from './generate-routing-utils.mjs';
import fs from 'fs';
import ts from 'typescript';

const extractInterfaces = routes =>
  routes
    .flatMap(r => [
      r.params ? [r.name, r.params] : undefined,
      ...extractInterfaces(r.subRoutes ?? [])
    ])
    .filter(r => !!r)
    .sort(compareByString(r => r[0]));

export const generateInterfaces = (file, resultRoutes, interfacesImports) => {
  const interfaces = extractInterfaces(resultRoutes);
  if (interfaces.length !== 0) {
    const imports = new Set(
      interfaces
        .map(i => i[1])
        .flatMap(i => i.map(o => o.type.typeName?.escapedText))
        .filter(i => !!i)
    );
    let source = printImports(interfacesImports, imports);
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
          .map(m => typescriptPrinter.printNode(ts.EmitHint.Unspecified, m))
          .join('\n    ');
        interf += '\n}\n';
        return interf;
      })
      .join('\n');
    fs.writeFile(file, source, err => {
      if (err) {
        throw err;
      }
    });
  }
};
