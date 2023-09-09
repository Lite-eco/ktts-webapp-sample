import { createContext, useContext } from 'react';

interface I18nContextInterface {
  language: 'En' | 'debug';
}

export const initialI18nContext: I18nContextInterface = {
  language: 'En'
};

export const I18nContext =
  createContext<I18nContextInterface>(initialI18nContext);

export const useI18n = <T extends { [key: string]: () => string }>(t: T) => {
  const { language } = useContext(I18nContext);
  if (language === 'debug') {
    const n = {} as any;
    // TODO generate later (for dev only), right now it iterates at each render
    // (which is very buggy with tables for example, see users management)
    Object.keys(t).forEach(k => {
      n[k] = () => '----';
    });
    return n as unknown as T;
  }
  return t;
};
