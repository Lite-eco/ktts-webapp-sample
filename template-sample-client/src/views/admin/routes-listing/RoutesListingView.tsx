/** @jsxImportSource @emotion/react */
import { useI18n } from '../../../hooks/i18n';
import { ApplicationRoute } from '../../../routing/ApplicationRoute.generated';
import { RouteLink } from '../../../routing/RouteLink';
import { router } from '../../../routing/router.generated';
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
      {router.map(route => (
        <RouteListingItem key={route.id} route={route} parentPath={''} />
      ))}
      <div
        css={css`
          margin-top: 20px;
          padding: 10px;
        `}
      >
        + <Link to={Math.random().toString()}>{t._404page()}</Link>
      </div>
    </div>
  );
};

const RouteListingItem = (props: {
  route: RouteObject;
  parentPath: string;
}) => {
  const path = props.route.path;
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
            padding-left: 10px;
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
  if (name === 'NotFound') {
    return null;
  }
  const fullPath = (props.parentPath + '/' + path).replace('//', '/');
  return (
    <div>
      <div
        css={css`
          padding: 10px;
        `}
      >
        {!fullPath.includes(':') ? (
          <RouteLink route={{ name } as ApplicationRoute}>
            <RouteLinkComp name={name} path={fullPath} />
          </RouteLink>
        ) : (
          <RouteLinkComp name={name} path={fullPath} />
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

const RouteLinkComp = (props: {
  name: ApplicationRoute['name'];
  path: string;
}) => (
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
