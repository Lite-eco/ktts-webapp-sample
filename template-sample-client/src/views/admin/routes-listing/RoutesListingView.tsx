/** @jsxImportSource @emotion/react */
import { useI18n } from '../../../hooks/i18n';
import { ApplicationRoute } from '../../../routing/ApplicationRoute.generated';
import { RouteLink } from '../../../routing/RouteLink';
import { routes } from '../../../routing/router.generated';
import { state } from '../../../state/state';
import { colors } from '../../../styles/vars';
import { RoutesListingViewI18n } from './RoutesListingView.i18n';
import { css } from '@emotion/react';
import { Link, RouteObject } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

export const RoutesListingView = () => {
  const userInfos = useRecoilValue(state.userInfos);
  const t = useI18n(RoutesListingViewI18n);
  if (userInfos?.role !== 'Admin') {
    return null;
  }
  return (
    <div
      css={css`
        display: flex;
        flex-direction: column;
        height: 100%;
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
  const t = useI18n(RoutesListingViewI18n);
  // if path is empty it's a container
  if (!path) {
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
  const name = props.route.id as ApplicationRoute['name'] | 'NotFound';
  const fullPath = (props.parentPath + '/' + path).replace('//', '/');
  return (
    <div>
      <div
        css={css`
          padding: 10px;
        `}
      >
        {name !== 'NotFound' && !fullPath.includes(':') && (
          <RouteLink route={{ name } as ApplicationRoute}>
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
