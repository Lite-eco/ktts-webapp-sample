import { I18nProvider } from './common-components/layout/I18nProvider';
import { StylesProviderContainer } from './common-components/layout/StylesProviderContainer';
import { router } from './generated/routing/router.generated';
import { SnackbarProvider } from 'notistack';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

global.log = (logged: any) => console.log(logged);

const root = document.getElementById('root');
if (root) {
  createRoot(root).render(
    <RecoilRoot>
      <StylesProviderContainer>
        <I18nProvider>
          <SnackbarProvider maxSnack={3}>
            <RouterProvider router={router} />
          </SnackbarProvider>
        </I18nProvider>
      </StylesProviderContainer>
    </RecoilRoot>
  );
}
