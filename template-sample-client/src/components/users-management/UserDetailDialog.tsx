/** @jsxImportSource @emotion/react */
import { UserId } from '../../generated/domain/Ids.generated';
import { UserInfos } from '../../generated/domain/User.generated';
import { GetUserInfosQueryResponse } from '../../generated/query/Queries.generated';
import { LoadingState } from '../../interfaces';
import { appContext } from '../../services/ApplicationContext';
import { CopyContentWidget } from '../common/CopyContentWidget';
import { RouteLink } from '../routing/RouteLink';
import { useGoTo } from '../routing/routing-utils';
import { t } from './UserDetailDialog.i18n';
import { RoleChip } from './UsersManagementTable';
import { css } from '@emotion/react';
import { ExpandMore as ExpandMoreIcon } from '@mui/icons-material';
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow
} from '@mui/material';
import * as React from 'react';
import { useEffect, useState } from 'react';

// TODO find a dedicated name ? it's almost a DialogView
export const UserDetailDialog = (props: { userId: UserId | undefined }) => {
  const [userInfos, setUserInfos] = useState<UserInfos>();
  const [loading, setLoading] = useState<LoadingState>('Idle');
  const goTo = useGoTo();
  useEffect(() => {
    if (props.userId) {
      // keep after if (props.userId) for dialog disappearing animation
      setUserInfos(undefined);
      setLoading('Loading');
      appContext.queryService
        .send<GetUserInfosQueryResponse>({
          objectType: 'GetUserInfosQuery',
          userId: props.userId
        })
        .then(r => {
          setLoading('Idle');
          setUserInfos(r.userInfos);
        });
    }
  }, [props.userId]);
  const close = () => goTo({ name: 'UsersManagementRoute' });
  return (
    <Dialog
      open={!!props.userId}
      onClose={close}
      maxWidth={'lg'}
      fullWidth={true}
      scroll="body"
    >
      <DialogTitle>{t.UserDetails()}</DialogTitle>
      <DialogContent>
        <DialogContentText>
          {loading === 'Loading' && (
            <div
              css={css`
                text-align: center;
              `}
            >
              <CircularProgress size={18} />
            </div>
          )}
          {userInfos && (
            <>
              <TableContainer component={Paper}>
                <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell>{t.Field()}</TableCell>
                      <TableCell align="left">{t.Value()}</TableCell>
                      <TableCell />
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    <TableRow
                      css={css`
                        &:last-child td,
                        &:last-child th {
                          border: 0;
                        }
                      `}
                    >
                      <TableCell component="th" scope="row">
                        {t.id()}
                      </TableCell>
                      <TableCell align="left">
                        <CopyContentWidget text={userInfos.id} />
                      </TableCell>
                      <TableCell align="left" />
                    </TableRow>
                    <TableRow
                      css={css`
                        &:last-child td,
                        &:last-child th {
                          border: 0;
                        }
                      `}
                    >
                      <TableCell component="th" scope="row">
                        {t.mail()}
                      </TableCell>
                      <TableCell align="left">
                        <CopyContentWidget text={userInfos.mail} />
                      </TableCell>
                      <TableCell align="left" />
                    </TableRow>
                    <TableRow
                      css={css`
                        &:last-child td,
                        &:last-child th {
                          border: 0;
                        }
                      `}
                    >
                      <TableCell component="th" scope="row">
                        {t.displayName()}
                      </TableCell>
                      <TableCell align="left">
                        {userInfos.displayName}
                      </TableCell>
                      <TableCell align="left" />
                    </TableRow>
                    <TableRow
                      css={css`
                        &:last-child td,
                        &:last-child th {
                          border: 0;
                        }
                      `}
                    >
                      <TableCell component="th" scope="row">
                        {t.roles()}
                      </TableCell>
                      <TableCell align="left">
                        {userInfos.roles.map(r => (
                          <RoleChip key={r} role={r} />
                        ))}
                      </TableCell>
                      <TableCell align="left">
                        <RouteLink
                          element="Button"
                          variant="outlined"
                          route={{
                            name: 'UsersManagementUserEditRolesRoute',
                            userId: userInfos.id
                          }}
                        >
                          {t.Edit()}
                        </RouteLink>
                      </TableCell>
                    </TableRow>
                  </TableBody>
                </Table>
              </TableContainer>
              <div
                css={css`
                  margin: 10px 0;
                `}
              >
                <Accordion>
                  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                    {t.RawJson()}
                  </AccordionSummary>
                  <AccordionDetails>
                    <pre>{JSON.stringify(userInfos, null, 2)}</pre>
                  </AccordionDetails>
                </Accordion>
              </div>
            </>
          )}
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={close}>{t.Close()}</Button>
      </DialogActions>
    </Dialog>
  );
};
