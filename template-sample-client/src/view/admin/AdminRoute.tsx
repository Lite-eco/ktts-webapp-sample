/** @jsxImportSource @emotion/react */
import { Outlet } from 'react-router-dom';
import { useUserState } from 'state/UserState';
import { NotFoundRoute } from 'view/not-found/NotFoundRoute';

export const AdminRoute = () => {
  const userInfos = useUserState(s => s.userInfos);
  if (!userInfos || userInfos.role !== 'Admin') {
    return <NotFoundRoute />;
  }
  return <Outlet />;
};
