/** @jsxImportSource @emotion/react */
import { RouteLink } from '../../../common-components/RouteLink';
import { ApplicationPath } from '../../../generated/routing/ApplicationPath.generated';
import { routes } from '../../../generated/routing/router.generated';
import { useI18n } from '../../../hooks/i18n';
import { useUserState } from '../../../state/UserState';
import { colors } from '../../../style/vars';
import { RoutesListingRouteI18n } from './RoutesListingRoute.i18n';
import { css } from '@emotion/react';
import { Link, RouteObject } from 'react-router-dom';

export const RoutesListingRoute = () => {
  const userInfos = useUserState(s => s.userInfos);
  const t = useI18n(RoutesListingRouteI18n);
  if (userInfos?.role !== 'Admin') {
    return null;
  }
  return (
    <div
      css={css`
        display: flex;
        flex-direction: column;
      `}
    >
      <h1>{t.RoutesListing()}</h1>
      {routes.map(route => (
        <RouteListingItem key={route.id} route={route} parentPath={''} />
      ))}
    </div>
  );
};

const RouteListingItem = (props: {
  route: RouteObject;
  parentPath: string;
}) => {
  const path = props.route.path;
  const t = useI18n(RoutesListingRouteI18n);
  // if path is an empty string it's a root route
  if (path === '') {
    return null;
  }
  // if path is undefined it's a container
  if (path === undefined) {
    return (
      <div
        css={css`
          margin: 4px 0;
          border: 1px solid ${colors.grey};
        `}
      >
        <div
          css={css`
            padding: 6px 20px;
            border-bottom: 1px solid ${colors.grey};
          `}
        >
          {props.route.id}
        </div>
        <div
          css={css`
            padding: 20px;
          `}
        >
          {(props.route.children ?? []).map(subRoute => (
            <RouteListingItem
              key={subRoute.id}
              route={subRoute}
              parentPath={''}
            />
          ))}
        </div>
      </div>
    );
  }
  const name = props.route.id as ApplicationPath['name'] | 'NotFound';
  const fullPath = (props.parentPath + '/' + path).replace('//', '/');
  return (
    <div>
      <div
        css={css`
          padding: 10px;
        `}
      >
        {name !== 'NotFound' && !fullPath.includes(':') && (
          <RouteLink route={{ name } as ApplicationPath}>
            <RouteLinkComp name={name} path={fullPath} />
          </RouteLink>
        )}
        {name !== 'NotFound' && fullPath.includes(':') && (
          <RouteLinkComp name={name} path={fullPath} />
        )}
        {name === 'NotFound' && (
          <Link to={`/not-found`}>
            <RouteLinkComp name={t._404page()} path={'/not-found'} />
          </Link>
        )}
      </div>
      <div
        css={css`
          padding-left: 10px;
        `}
      >
        {(props.route.children ?? []).map(subRoute => (
          <RouteListingItem
            key={subRoute.id}
            route={subRoute}
            parentPath={fullPath}
          />
        ))}
      </div>
    </div>
  );
};

const RouteLinkComp = (props: { name: string; path: string }) => (
  <div>
    <div
      css={css`
        font-weight: bold;
      `}
    >
      {props.name}
    </div>
    <div
      css={css`
        font-size: 0.8rem;
        color: ${colors.grey};
      `}
    >
      {props.path}
    </div>
  </div>
);
