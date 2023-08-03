/** @jsxImportSource @emotion/react */
import * as React from 'react';
import { useRecoilState } from 'recoil';
import { MainContainer } from '../container/MainContainer';
import { RouteLink } from '../routing/RouteLink';
import { state } from '../state/state';
import { ComponentsDemonstration } from '../component/ComponentsDemonstration';
import { RouteLinkDemonstration } from '../component/RouteLinkDemonstration';

export const RootView = () => {
  const [userInfos] = useRecoilState(state.userInfos);
  return (
    <MainContainer>
      {!userInfos && (
        <RouteLink
          route={{
            name: 'LoginRoute'
          }}
        >
          Login
        </RouteLink>
      )}
      {userInfos && <div>You're connected</div>}
      <ComponentsDemonstration />
      <RouteLinkDemonstration />
      {userInfos && userInfos.roles.includes('Admin') && (
        <RouteLink
          route={{
            name: 'UsersManagementRoute'
          }}
        >
          Users management
        </RouteLink>
      )}
    </MainContainer>
  );
};
