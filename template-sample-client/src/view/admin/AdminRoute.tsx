/** @jsxImportSource @emotion/react */
import { RouteLink } from '../../common-components/RouteLink';
import { ApplicationPath } from '../../generated/routing/ApplicationPath.generated';
import { routerPathMap } from '../../generated/routing/routerPathMap.generated';
import { state } from '../../state/state';
import { dictEntries } from '../../utils/nominal-class';
import { useTypedMatches } from '../../utils/routing-utils';
import { NotFoundRoute } from '../not-found/NotFoundRoute';
import { css } from '@emotion/react';
import { Outlet } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

export const AdminRoute = () => {
  const matches = useTypedMatches();
  const displayLinks = matches[matches.length - 1].id === 'Admin';
  const userInfos = useRecoilValue(state.userInfos);
  if (!userInfos || userInfos.role !== 'Admin') {
    return <NotFoundRoute />;
  }
  return (
    <>
      {displayLinks && (
        <>
          <h1>Admin</h1>
          <div>
            {dictEntries(routerPathMap)
              .filter(([name]) => name.startsWith('Admin/'))
              .map(([name, path]) => {
                const subRoutes = name.split('/');
                if (subRoutes.length > 2) {
                  return null;
                }
                return (
                  <div
                    css={css`
                      padding: 10px;
                    `}
                  >
                    <RouteLink route={{ name } as ApplicationPath}>
                      {subRoutes[1]}
                    </RouteLink>
                  </div>
                );
              })}
          </div>
        </>
      )}
      <Outlet />
    </>
  );
};
