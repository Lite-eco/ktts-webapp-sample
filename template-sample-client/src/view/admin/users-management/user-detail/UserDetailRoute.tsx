/** @jsxImportSource @emotion/react */
import { AdminUserInfos } from 'generated/domain/User.generated';
import { AdminGetUserInfosQueryResponse } from 'generated/query/Queries.generated';
import { AdminUsersManagementUserDetailPath } from 'generated/routing/ApplicationPath.generated';
import { LoadingStatus } from 'interfaces';
import { useEffect, useState } from 'react';
import { Outlet, useOutletContext } from 'react-router-dom';
import { appContext } from 'services/ApplicationContext';
import { useTypedParams } from 'utils/routing-utils';
import { UsersManagementOutletContext } from 'view/admin/users-management/UsersManagementRoute';
import { UserDetailDialog } from 'view/admin/users-management/user-detail/components/UserDetailDialog';

export interface UsersManagementUserDetailOutletContext {
  userInfos: AdminUserInfos | undefined;
  updateUserInfos: (userInfos: AdminUserInfos) => void;
  loadingUserInfos: LoadingStatus;
}

export const UserDetailRoute = () => {
  const params = useTypedParams<AdminUsersManagementUserDetailPath>();
  const outletContext = useOutletContext<UsersManagementOutletContext>();
  const [userInfos, setUserInfos] = useState<AdminUserInfos>();
  const [loadingUserInfos, setLoadingUserInfos] =
    useState<LoadingStatus>('Idle');
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
