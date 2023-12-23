import { I18nProvider } from 'common-components/layout/I18nProvider';
import { StylesProviderContainer } from 'common-components/layout/StylesProviderContainer';
import { router } from 'generated/routing/router.generated';
import { SnackbarProvider } from 'notistack';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';

global.log = console.log;

const root = document.getElementById('root');
if (root) {
  createRoot(root).render(
    <StylesProviderContainer>
      <I18nProvider>
        <SnackbarProvider maxSnack={3}>
          <RouterProvider router={router} />
        </SnackbarProvider>
      </I18nProvider>
    </StylesProviderContainer>
  );
}
