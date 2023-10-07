/** @jsxImportSource @emotion/react */
import { RouteLink } from '../../common-components/RouteLink';
import { ApplicationPath } from '../../generated/routing/ApplicationPath.generated';
import { routerPathMap } from '../../generated/routing/routerPathMap.generated';
import { dictEntries } from '../../utils/nominal-class';
import { css } from '@emotion/react';

export const AdminRootRoute = () => {
  return (
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
  );
};
