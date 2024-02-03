/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { AdminUserInfos } from 'generated/domain/User.generated';
import { AdminGetUsersQueryResponse } from 'generated/query/Queries.generated';
import { useI18n } from 'hooks/i18n';
import { LoadingStatus } from 'interfaces';
import { useSnackbar } from 'notistack';
import { useEffect, useState } from 'react';
import { Outlet } from 'react-router-dom';
import { appContext } from 'services/ApplicationContext';
import { UsersManagementRouteI18n } from 'view/admin/users-management/UsersManagementRoute.i18n';
import { UsersManagementTable } from 'view/admin/users-management/components/UsersManagementTable';

export interface UsersManagementOutletContext {
  updateUserInfos: (userInfos: AdminUserInfos) => void;
}

export const UsersManagementRoute = () => {
  const [users, setUsers] = useState<AdminUserInfos[]>([]);
  const [loading, setLoading] = useState<LoadingStatus>('Idle');
  const { enqueueSnackbar } = useSnackbar();
  // TODO would probably be better to reload data
  const updateUserInfos = (userInfos: AdminUserInfos) =>
    setUsers([
      ...users.map(u => {
        if (u.id === userInfos.id) {
          return userInfos;
        } else {
          return u;
        }
      })
    ]);
  const t = useI18n(UsersManagementRouteI18n);
  useEffect(() => {
    setLoading('Loading');
    appContext.queryService
      .send<AdminGetUsersQueryResponse>({
        objectType: 'AdminGetUsersQuery'
      })
      .then(r => {
        setUsers(r.users);
        setLoading('Idle');
      })
      .catch(() => {
        setLoading('Error');
        enqueueSnackbar(t.AnErrorOccurredWhileRetrievingData(), {
          variant: 'error'
        });
      });
  }, [enqueueSnackbar, t]);
  const context: UsersManagementOutletContext = { updateUserInfos };
  return (
    <div
      css={css`
        display: flex;
        flex-direction: column;
        height: 100%;
      `}
    >
      <h1>{t.UsersManagement()}</h1>
      <UsersManagementTable users={users} loading={loading} />
      <Outlet context={context} />
    </div>
  );
};
