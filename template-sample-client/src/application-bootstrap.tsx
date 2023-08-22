import { router } from './routing/router';
import { Root } from './views/containers/Root';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

global.log = (logged: any) => console.log(logged);

const root = document.getElementById('root');
if (root) {
  createRoot(root).render(
    <RecoilRoot>
      <Root>
        <RouterProvider router={router} />
      </Root>
    </RecoilRoot>
  );
}
