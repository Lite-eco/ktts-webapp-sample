import ts from 'typescript';

export const notFoundPath = '*';

export const typescriptPrinter = ts.createPrinter({
  newLine: ts.NewLineKind.LineFeed
});

export class Collections {
  static groupBy = (a, key) => {
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
}
