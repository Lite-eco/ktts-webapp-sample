/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { Warning as WarningIcon } from '@mui/icons-material';
import {
  Autocomplete,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField
} from '@mui/material';
import { LoadingStatusButton } from 'view/components/LoadingButton';
import { roleEnumValues } from 'domain/enums';
import { Errors } from 'errors';
import { UserId } from 'generated/domain/Ids.generated';
import { AdminUserInfos, Role } from 'generated/domain/User.generated';
import { useI18n } from 'hooks/i18n';
import { LoadingStatus } from 'interfaces';
import { PropsWithChildren, useEffect, useState } from 'react';
import { appContext } from 'services/ApplicationContext';
import { useUserState } from 'state/UserState';
import { colors } from 'style/vars';
import { navigateTo } from 'utils/routing-utils';
import { UserEditRoleDialogI18n } from 'view/admin/users-management/user-detail/edit-role/components/UserEditRoleDialog.i18n';

export const UserEditRoleDialog = (props: {
  userId: UserId;
  userInfos: AdminUserInfos | undefined;
  updateUserInfos: (user: AdminUserInfos) => void;
  loadingUserInfos: LoadingStatus;
}) => {
  const [role, setRole] = useState<Role>();
  const [updateLoading, setUpdateLoading] = useState<LoadingStatus>('Idle');
  const loggedInUserInfos = useUserState(s => s.userInfos);
  if (!loggedInUserInfos) {
    throw Errors._fe2e1fc7();
  }
  useEffect(() => setRole(props.userInfos?.role), [props.userInfos]);
  const close = () =>
    navigateTo({
      name: 'Admin/UsersManagement/UserDetail',
      userId: props.userId
    });
  const save = () => {
    const userInfos = props.userInfos;
    if (!userInfos) {
      throw Errors._8ab803a9();
    }
    if (role && role !== userInfos.role) {
      setUpdateLoading('Loading');
      appContext.commandService
        .send({
          objectType: 'AdminUpdateRoleCommand',
          userId: userInfos.id,
          role
        })
        .then(() => {
          setUpdateLoading('Idle');
          props.updateUserInfos({ ...userInfos, role });
          close();
        });
    } else {
      close();
    }
  };
  const t = useI18n(UserEditRoleDialogI18n);
  return (
    <Dialog
      open={true}
      onClose={close}
      maxWidth={'md'}
      fullWidth={true}
      scroll="body"
    >
      <DialogTitle>{t.EditUserRole()}</DialogTitle>
      <DialogContent>
        <DialogContentText>
          {props.loadingUserInfos === 'Loading' && (
            <div
              css={css`
                text-align: center;
              `}
            >
              <CircularProgress size={18} />
            </div>
          )}
          <div>
            {role && (
              <Autocomplete
                options={roleEnumValues}
                value={role}
                renderInput={params => <TextField {...params} />}
                onChange={(_, role) => setRole(role)}
                disableClearable={true}
              />
            )}
            {props.userInfos &&
              loggedInUserInfos.id === props.userInfos.id &&
              role !== 'Admin' && (
                <WarningMessage>{t.WarningAdmin()}</WarningMessage>
              )}
          </div>
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={close}>{t.Close()}</Button>
        <LoadingStatusButton loadingStatus={updateLoading} onClick={save}>
          {t.Save()}
        </LoadingStatusButton>
      </DialogActions>
    </Dialog>
  );
};

const WarningMessage = (props: PropsWithChildren) => (
  <div
    css={css`
      color: ${colors.white};
      background: ${colors.errorBackground};
      border: 1px solid ${colors.errorRed};
      border-radius: 4px;
      margin: 10px 0;
      padding: 10px;
      display: flex;
    `}
  >
    <WarningIcon
      css={css`
        margin-right: 20px;
      `}
    />
    {props.children}
  </div>
);
