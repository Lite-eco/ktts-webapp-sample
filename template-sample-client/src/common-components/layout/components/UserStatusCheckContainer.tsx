/** @jsxImportSource @emotion/react */
import { UserStatus } from '../../../generated/domain/User.generated';
import { GetUserStatusQueryResponse } from '../../../generated/query/Queries.generated';
import { useI18n } from '../../../hooks/i18n';
import { appContext } from '../../../services/ApplicationContext';
import { state } from '../../../state/state';
import { assertUnreachable } from '../../../utils';
import { nominal } from '../../../utils/nominal-class';
import { navigateTo } from '../../../utils/routing-utils';
import { UserStatusCheckContainerI18n } from './UserStatusCheckContainer.i18n';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle
} from '@mui/material';
import { PropsWithChildren, useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';

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
  const [userInfos, setUserInfos] = useRecoilState(state.userInfos);
  const [displayPopup, setDisplayPopup] = useState(false);
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
            }
            setUserInfos({ ...userInfos, status: r.status });
          });
      }, 2000);
      return () => clearInterval(intervalId); //This is important
    }
    return () => {};
  }, [userInfos, setUserInfos]);
  const mailValidationToken = new URLSearchParams(window.location.search).get(
    'mailValidation'
  );
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
          setUserInfos({
            ...userInfos,
            status: 'Active'
          });
        }
        navigateTo({ name: 'Root' }, true);
        setDisplayPopup(true);
      });
  }
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
