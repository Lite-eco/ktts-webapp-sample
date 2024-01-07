/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { Navbar } from 'view/components/layout/components/Navbar';
import { UserStatusCheckContainer } from 'view/components/layout/components/UserStatusCheckContainer';
import { Outlet } from 'react-router-dom';

export const MainLayout = () => (
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
