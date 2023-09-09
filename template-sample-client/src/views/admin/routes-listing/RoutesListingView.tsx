/** @jsxImportSource @emotion/react */
import { ApplicationRoute } from '../../../routing/ApplicationRoute.generated';
import { RouteLink } from '../../../routing/RouteLink';
import { router } from '../../../routing/router.generated';
import { state } from '../../../state/state';
import { colors } from '../../../styles/vars';
import { t } from './RoutesListingView.i18n';
import { css } from '@emotion/react';
import { Link, RouteObject } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

export const RoutesListingView = () => {
  const userInfos = useRecoilValue(state.userInfos);
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
      {router
        .filter(r => r.id !== 'NotFound')
        .map(route => (
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
  const name = props.route.id as ApplicationRoute['name'] | undefined;
  const path = props.route.path;
  if (!name || !path) {
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
