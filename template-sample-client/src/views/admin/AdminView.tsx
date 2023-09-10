/** @jsxImportSource @emotion/react */
import { ApplicationRoute } from '../../routing/ApplicationRoute.generated';
import { RouteLink } from '../../routing/RouteLink';
import { routePathMap } from '../../routing/routePathMap.generated';
import { useTypedMatches } from '../../routing/routing-utils';
import { state } from '../../state/state';
import { dictEntries } from '../../utils/nominal-class';
import { NotFoundView } from '../not-found/NotFoundView';
import { css } from '@emotion/react';
import { Outlet } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

export const AdminView = () => {
  const matches = useTypedMatches();
  const displayLinks = matches[matches.length - 1].id === 'Admin';
  const userInfos = useRecoilValue(state.userInfos);
  if (!userInfos || userInfos.role !== 'Admin') {
    return <NotFoundView />;
  }
  return (
    <>
      {displayLinks && (
        <>
          <h1>Admin</h1>
          <div>
            {dictEntries(routePathMap)
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
                    <RouteLink route={{ name } as ApplicationRoute}>
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
