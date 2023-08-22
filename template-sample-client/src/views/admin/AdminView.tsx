/** @jsxImportSource @emotion/react */
import { RouteLink } from '../../routing/RouteLink';
import { MainContainer } from '../containers/MainContainer';
import { t } from './AdminView.i18n';
import { Outlet, useMatches } from 'react-router-dom';

export const AdminView = () => {
  const matches = useMatches();
  const displayLinks = matches.length === 1;
  return (
    <MainContainer>
      {displayLinks && (
        <>
          <RouteLink route={{ name: 'Admin/ManualCommand' }}>
            {t.ManualCommands()}
          </RouteLink>
          <br />
          <RouteLink route={{ name: 'Admin/UsersManagement' }}>
            {t.UsersManagement()}
          </RouteLink>
        </>
      )}
      <Outlet />
    </MainContainer>
  );
};
