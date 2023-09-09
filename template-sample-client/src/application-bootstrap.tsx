import { router } from './routing/router.generated';
import { StylesProviderContainer } from './views/containers/StylesProviderContainer';
import { SnackbarProvider } from 'notistack';
import { createRoot } from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

global.log = (logged: any) => console.log(logged);

const root = document.getElementById('root');
if (root) {
  createRoot(root).render(
    <RecoilRoot>
      <StylesProviderContainer>
        <SnackbarProvider maxSnack={3}>
          <RouterProvider router={createBrowserRouter(router)} />
        </SnackbarProvider>
      </StylesProviderContainer>
    </RecoilRoot>
  );
}
