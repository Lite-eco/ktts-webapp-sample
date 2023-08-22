import { ApplicationRouter } from './routing/ApplicationRouter';
import { Root } from './views/containers/Root';
import { createRoot } from 'react-dom/client';
import { RecoilRoot } from 'recoil';

global.log = (logged: any) => console.log(logged);

const root = document.getElementById('root');
if (root) {
  createRoot(root).render(
    <RecoilRoot>
      <Root>
        <ApplicationRouter />
      </Root>
    </RecoilRoot>
  );
}
