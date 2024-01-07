/** @jsxImportSource @emotion/react */
import { I18nContext, initialI18nContext } from 'hooks/i18n';
import { PropsWithChildren, useCallback, useEffect, useState } from 'react';

export const I18nProvider = (props: PropsWithChildren) => {
  const [language, setLanguage] = useState<'En' | 'debug'>(
    initialI18nContext.language
  );
  // from https://devtrium.com/posts/how-keyboard-shortcut
  const handleKeyPress = useCallback(
    (e: KeyboardEvent) => {
      if (e.ctrlKey && e.key === 't') {
        language === initialI18nContext.language
          ? setLanguage('debug')
          : setLanguage(initialI18nContext.language);
      }
    },
    [language]
  );
  useEffect(() => {
    document.addEventListener('keydown', handleKeyPress);
    return () => document.removeEventListener('keydown', handleKeyPress);
  }, [handleKeyPress]);
  return (
    <I18nContext.Provider value={{ language }}>
      {props.children}
    </I18nContext.Provider>
  );
};
