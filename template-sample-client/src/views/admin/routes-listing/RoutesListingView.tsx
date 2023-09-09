/** @jsxImportSource @emotion/react */
import { UserInfos } from '../../../generated/domain/User.generated';
import { ApplicationRoute } from '../../../routing/ApplicationRoute.generated';
import { RouteLink } from '../../../routing/RouteLink';
import { routePathMap } from '../../../routing/routePathMap.generated';
import { state } from '../../../state/state';
import { colors } from '../../../styles/vars';
import { dictEntries } from '../../../utils/nominal-class';
import { t } from './RoutesListingView.i18n';
import { css } from '@emotion/react';
import { useRecoilValue } from 'recoil';

export interface UsersManagementOutletContext {
  updateUserInfos: (userInfos: UserInfos) => void;
}

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
      {dictEntries(routePathMap).map(([name, path]) => (
        <div
          key={name}
          css={css`
            padding: 10px;
          `}
        >
          {(() => {
            if (!path.includes(':')) {
              return (
                <RouteLink route={{ name } as unknown as ApplicationRoute}>
                  <RouteLinkComp name={name} path={path} />
                </RouteLink>
              );
            } else {
              return <RouteLinkComp name={name} path={path} />;
            }
          })()}
        </div>
      ))}
    </div>
  );
};
