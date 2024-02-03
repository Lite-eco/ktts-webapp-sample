/** @jsxImportSource @emotion/react */
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle
} from '@mui/material';
import { UserStatusCheckContainerI18n } from 'view/components/layout/components/UserStatusCheckContainer.i18n';
import { UserStatus } from 'generated/domain/User.generated';
import { GetUserStatusQueryResponse } from 'generated/query/Queries.generated';
import { useI18n } from 'hooks/i18n';
import { PropsWithChildren, useEffect, useState } from 'react';
import { appContext } from 'services/ApplicationContext';
import { useUserState } from 'state/UserState';
import { assertUnreachable } from 'utils';
import { nominal } from 'utils/nominal-class';
import { navigateTo } from 'utils/routing-utils';

const UserStatusContent = (
  props: PropsWithChildren<{ status: UserStatus | undefined }>
) => {
  const t = useI18n(UserStatusCheckContainerI18n);
  switch (props.status) {
    case undefined:
    case 'Active':
      return props.children;
    case 'MailValidationPending':
      return <div>{t.MailMustBeValidated()}</div>;
    case 'Disabled':
      return <div>{t.DisabledAccount()}</div>;
    default:
      assertUnreachable(props.status);
  }
};

export const UserStatusCheckContainer = (props: PropsWithChildren) => {
  const userInfos = useUserState(s => s.userInfos);
  const updateUserStatus = useUserState(s => s.updateUserStatus);
  const [displayPopup, setDisplayPopup] = useState(false);
  const mailValidationToken = new URLSearchParams(window.location.search).get(
    'mailValidation'
  );
  useEffect(() => {
    if (userInfos?.status === 'MailValidationPending') {
      const intervalId = setInterval(() => {
        appContext.queryService
          .send<GetUserStatusQueryResponse>({
            objectType: 'GetUserStatusQuery'
          })
          .then(r => {
            if (r.status !== 'MailValidationPending') {
              clearInterval(intervalId);
              setDisplayPopup(true);
            }
            updateUserStatus(r.status);
          });
      }, 2000);
      return () => clearInterval(intervalId);
    }
  }, [userInfos, updateUserStatus]);
  useEffect(() => {
    if (mailValidationToken) {
      const [token, userId] = mailValidationToken.split('-');
      appContext.commandService
        .send({
          objectType: 'ValidateMailCommand',
          token: nominal(token)
        })
        .then(() => {
          // validation is done whether the user is connected or not
          // (and if there's a session, it's not necessarily with the validated user)
          if (userInfos?.id === userId) {
            updateUserStatus('Active');
          }
          navigateTo({ name: 'Root' }, true);
          setDisplayPopup(true);
        });
    }
  }, [mailValidationToken, userInfos, updateUserStatus]);
  const t = useI18n(UserStatusCheckContainerI18n);
  return (
    <>
      <UserStatusContent status={userInfos?.status}>
        {props.children}
      </UserStatusContent>
      <Dialog open={displayPopup} onClose={() => setDisplayPopup(false)}>
        <DialogTitle>{t.UserAccountMailValidation()}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            {t.TheMailHasBeenValidated()}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDisplayPopup(false)} autoFocus>
            {t.ok()}
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};
