/** @jsxImportSource @emotion/react */
import { AdminUserInfos } from '../../../../generated/domain/User.generated';
import { AdminGetUserInfosQueryResponse } from '../../../../generated/query/Queries.generated';
import { LoadingState } from '../../../../interfaces';
import { AdminUsersManagementUserDetailRoute } from '../../../../routing/ApplicationRoute.generated';
import { useTypedParams } from '../../../../routing/routing-utils';
import { appContext } from '../../../../services/ApplicationContext';
import { UsersManagementOutletContext } from '../UsersManagementView';
import { UserDetailDialog } from './components/UserDetailDialog';
import { useEffect, useState } from 'react';
import { Outlet, useOutletContext } from 'react-router-dom';

export interface UsersManagementUserDetailOutletContext {
  userInfos: AdminUserInfos | undefined;
  updateUserInfos: (userInfos: AdminUserInfos) => void;
  loadingUserInfos: LoadingState;
}

export const UserDetailView = () => {
  const params = useTypedParams<AdminUsersManagementUserDetailRoute>();
  const outletContext = useOutletContext<UsersManagementOutletContext>();
  const [userInfos, setUserInfos] = useState<AdminUserInfos>();
  const [loadingUserInfos, setLoadingUserInfos] =
    useState<LoadingState>('Idle');
  const updateUserInfos = (userInfos: AdminUserInfos) => {
    outletContext.updateUserInfos(userInfos);
    setUserInfos(userInfos);
  };
  useEffect(() => {
    setUserInfos(undefined);
    setLoadingUserInfos('Loading');
    appContext.queryService
      .send<AdminGetUserInfosQueryResponse>({
        objectType: 'AdminGetUserInfosQuery',
        userId: params.userId
      })
      .then(r => {
        setLoadingUserInfos('Idle');
        setUserInfos(r.userInfos);
      });
  }, [params.userId]);
  const context: UsersManagementUserDetailOutletContext = {
    userInfos,
    updateUserInfos,
    loadingUserInfos
  };
  return (
    <>
      <UserDetailDialog
        userInfos={userInfos}
        loadingUserInfos={loadingUserInfos}
      />
      <Outlet context={context} />
    </>
  );
};
