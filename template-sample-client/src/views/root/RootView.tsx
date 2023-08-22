/** @jsxImportSource @emotion/react */
import { RouteLink } from '../../routing/RouteLink';
import { state } from '../../state/state';
import { MainContainer } from '../containers/MainContainer';
import { t } from './RootView.i18n';
import { useRecoilState } from 'recoil';

export const RootView = () => {
  const [userInfos] = useRecoilState(state.userInfos);
  return (
    <MainContainer>
      {!userInfos && (
        <RouteLink
          route={{
            name: 'LoginRoute'
          }}
        >
          {t.Login()}
        </RouteLink>
      )}
      {userInfos && <div>{t.YouAreConnected()}</div>}
    </MainContainer>
  );
};
