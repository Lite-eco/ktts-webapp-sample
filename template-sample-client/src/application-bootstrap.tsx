import { MaterialUiRoot } from './components/containers/MaterialUiRoot';
import { Root } from './components/containers/Root';
import { ApplicationRouter } from './components/routing/ApplicationRouter';
import * as React from 'react';
import { createRoot } from 'react-dom/client';
import { RecoilRoot } from 'recoil';

global.log = (logged: any) => console.log(logged);

const root = document.getElementById('root');
if (root) {
  createRoot(root).render(
    <RecoilRoot>
      <MaterialUiRoot>
        <Root>
          <ApplicationRouter />
        </Root>
      </MaterialUiRoot>
    </RecoilRoot>
  );
}
