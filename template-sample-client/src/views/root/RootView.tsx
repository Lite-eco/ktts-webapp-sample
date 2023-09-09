/** @jsxImportSource @emotion/react */
import { RouteLink } from '../../routing/RouteLink';
import { state } from '../../state/state';
import { MainViewContainer } from '../containers/MainViewContainer';
import { t } from './RootView.i18n';
import { useRecoilValue } from 'recoil';

export const RootView = () => {
  const userInfos = useRecoilValue(state.userInfos);
  return (
    <MainViewContainer>
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
    </MainViewContainer>
  );
};
