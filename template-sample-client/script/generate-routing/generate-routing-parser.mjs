import { Collections, notFoundPath } from './generate-routing-utils.mjs';
import fs from 'fs';
import ts from 'typescript';

class Parser {
  static extractTypeMember = (node, fieldName) =>
    node.type.members.find(m => m?.name?.escapedText === fieldName);

  static parseMember = (member, parentName, parentPath, parentParams) => {
    const name = member.name.escapedText;
    const fullName = parentName ? `${parentName}/${name}` : name;
    console.log('[debug] parsing route ' + fullName);
    const path = Parser.extractTypeMember(member, 'path').type.literal.text;
    const fullPath = parentPath ? parentPath + '/' + path : path;
    const component = Parser.extractTypeMember(member, 'component').type
      .exprName.escapedText;
    const layout = Parser.extractTypeMember(member, 'layout')?.type.exprName
      .escapedText;
    const rootComponent = Parser.extractTypeMember(member, 'rootComponent')
      ?.type.exprName.escapedText;
    const params =
      path !== notFoundPath
        ? [
            ...(parentParams ?? []),
            ...(Parser.extractTypeMember(member, 'params')?.type?.members ?? [])
          ]
        : undefined;
    // output
    const result = {
      id: fullName,
      name: fullName,
      path: path ? path : '/',
      fullPath: fullPath,
      component,
      layout,
      rootComponent,
      params
    };
    const subRoutes = Parser.extractTypeMember(member, 'subRoutes');
    if (subRoutes) {
      result['subRoutes'] = subRoutes.type.members.map(m =>
        Parser.parseMember(m, fullName, fullPath, params)
      );
    }
    return result;
  };
}

export const parseFile = file => {
  // output
  const interfacesImports = [];
  const resultRoutes = [];

  // read route-dsl.ts content
  const node = ts.createSourceFile(
    'x.ts',
    fs.readFileSync(file, 'utf8'),
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
      const rawResults = s.type.members.map(m => Parser.parseMember(m));
      // layouts are at level 0 only, no need to recursively groupBy
      const gp = Collections.groupBy(rawResults, c => c.layout ?? '_noLayout');
      const layoutResults = Object.entries(gp)
        .filter(([key]) => key !== '')
        .map(([key, value]) => ({
          id: key,
          // layout has no name and no path
          component: key,
          subRoutes: value
        }));
      resultRoutes.push(...layoutResults);
      resultRoutes.push(...(gp['_noLayout'] ?? []));
    }
  });
  return { interfacesImports, resultRoutes };
};
