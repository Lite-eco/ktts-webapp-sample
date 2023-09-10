/** @jsxImportSource @emotion/react */
import { Navbar } from './components/Navbar';
import { UserStatusCheckContainer } from './components/UserStatusCheckContainer';
import { css } from '@emotion/react';
import { Outlet } from 'react-router-dom';

export const MainViewContainer = () => (
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
      <UserStatusCheckContainer>
        <Outlet />
      </UserStatusCheckContainer>
    </div>
  </div>
);
