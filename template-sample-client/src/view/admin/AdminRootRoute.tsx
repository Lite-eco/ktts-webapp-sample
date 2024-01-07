/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { RouteLink } from 'view/components/RouteLink';
import { ApplicationPath } from 'generated/routing/ApplicationPath.generated';
import { routerPathMap } from 'generated/routing/routerPathMap.generated';
import { useI18n } from 'hooks/i18n';
import { dictEntries } from 'utils/nominal-class';
import { AdminRootRouteI18n } from 'view/admin/AdminRootRoute.i18n';

export const AdminRootRoute = () => {
  const t = useI18n(AdminRootRouteI18n);
  return (
    <>
      <h1>{t.Admin()}</h1>
      <div>
        {dictEntries(routerPathMap)
          .filter(([name]) => name.startsWith('Admin/'))
          .map(([name]) => {
            const subRoutes = name.split('/');
            if (subRoutes.length > 2) {
              return null;
            }
            return (
              <div
                key={name}
                css={css`
                  padding: 10px;
                `}
              >
                <RouteLink path={{ name } as ApplicationPath}>
                  {subRoutes[1]}
                </RouteLink>
              </div>
            );
          })}
      </div>
    </>
  );
};
