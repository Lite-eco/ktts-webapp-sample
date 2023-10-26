/** @jsxImportSource @emotion/react */
import { RouteLink } from '../../common-components/RouteLink';
import { useI18n } from '../../hooks/i18n';
import { useUserState } from '../../state/UserState';
import { RootRouteI18n } from './RootRoute.i18n';

export const RootRoute = () => {
  const userInfos = useUserState(s => s.userInfos);
  const t = useI18n(RootRouteI18n);
  return (
    <>
      {!userInfos && (
        <RouteLink
          route={{
            name: 'Login'
          }}
        >
          {t.Login()}
        </RouteLink>
      )}
      {userInfos && <div>{t.YouAreConnected()}</div>}
    </>
  );
};
