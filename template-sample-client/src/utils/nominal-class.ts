export type NominalItem = NominalString<any> | NominalNumber<any>;

export type NominalString<T extends string> = string &
  TypeGuardedNominalString<T>;

abstract class TypeGuardedNominalString<T extends string> {
  private _typeGuard!: T;
}

export type NominalNumber<T extends string> = number &
  TypeGuardedNominalNumber<T>;

abstract class TypeGuardedNominalNumber<T extends string> {
  private _typeGuard!: T;
}

// [doc] undefined is needed for interfaces with nullable Nominal
export const nominal = <
  T extends NominalNumber<any> | NominalString<any> | undefined
>(
  value: string | number
) => value as T;

type DictKey = NominalItem | string;

export class Dict<K extends DictKey, T> {
  private _typeGuardKey!: K;
  private _typeGuardValue!: T;
}

export const dict = <K extends DictKey, T>() => ({}) as Dict<K, T>;

export const associateBy = <K extends DictKey, T>(
  a: T[],
  key: (i: T) => K
): Dict<K, T> => {
  const d = dict<K, T>();
  a.forEach(i => setMutable(d, key(i), i));
  return d;
};

export const pairsToDict = <K extends DictKey, T>(pairs: [K, T][] = []) =>
  // @ts-ignore
  Object.fromEntries(pairs) as Dict<K, T>;

export const get = <K extends DictKey, T>(
  dict: Dict<K, T>,
  key: K
): T | undefined =>
  // @ts-ignore
  dict[key];

export const getValue = <K extends DictKey, T>(dict: Dict<K, T>, key: K): T => {
  const r = get(dict, key);
  if (!r) {
    throw new Error(`Could not find item ${key}`);
  }
  return r;
};

export const setValue = <K extends DictKey, T>(
  dict: Dict<K, T>,
  key: K,
  value: T
): Dict<K, T> => {
  const newDict = { ...dict } as Dict<K, T>;
  setMutable(newDict, key, value);
  return newDict;
};

export const setAll = <K extends DictKey, T>(
  dict: Dict<K, T>,
  values: T[],
  key: (i: T) => K
) => {
  const result = { ...dict } as Dict<K, T>;
  values.forEach(i => setMutable(result, key(i), i));
  return result;
};

export const setMutable = <K extends DictKey, T>(
  dict: Dict<K, T>,
  key: K,
  value: T
) => {
  // @ts-ignore
  dict[key] = value;
};

export const dictKeys = <K extends DictKey, T>(dict: Dict<K, T>) =>
  Object.keys(dict) as K[];

export const dictValues = <K extends DictKey, T>(dict: Dict<K, T>) =>
  Object.values(dict) as T[];

export const dictEntries = <K extends DictKey, T>(dict: Dict<K, T>) =>
  Object.entries(dict) as [K, T][];

export const deleteFromDict = <K extends DictKey, T>(
  dict: Dict<K, T>,
  ...keys: K[]
): Dict<K, T> => {
  const newDict = { ...dict } as Dict<K, T>;
  keys.forEach(k => {
    // @ts-ignore
    delete newDict[k];
  });
  return newDict;
};

// TODO[tmpl][doc] last item will replace
export const mergeDicts = <K extends DictKey, T>(...dicts: Dict<K, T>[]) => {
  const result = dict<K, T>();
  dicts.forEach(dict => {
    dictEntries(dict).forEach(([key, value]) => {
      setMutable(result, key, value);
    });
  });
  return result;
};

export const groupBy = <K extends DictKey, T>(
  a: T[],
  key: (i: T) => K
): Dict<K, T[]> => {
  const map = dict<K, T[]>();
  a.forEach(item => {
    const k = key(item);
    const existing = get(map, k);
    const list = existing ? existing : [];
    if (!existing) {
      setMutable(map, k, list);
    }
    list.push(item);
  });
  return map;
};

export const flatMap = <T, R>(a: T[], lambda: (o: T, i: number) => R) =>
  Array.prototype.concat.apply([], a.map(lambda));
