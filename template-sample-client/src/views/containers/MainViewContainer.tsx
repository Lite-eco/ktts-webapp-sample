/** @jsxImportSource @emotion/react */
import { Navbar } from './components/Navbar';
import { UserStatusCheckContainer } from './components/UserStatusCheckContainer';
import { css } from '@emotion/react';
import { PropsWithChildren } from 'react';

export const MainViewContainer = (props: PropsWithChildren) => (
  <div
    css={css`
      width: 100vw;
      height: 100vh;
      display: flex;
      flex-direction: column;
    `}
  >
    <Navbar />
    <div
      css={css`
        flex: 1;
        overflow: scroll;
        padding: 10px;
      `}
    >
      <UserStatusCheckContainer>{props.children}</UserStatusCheckContainer>
    </div>
  </div>
);