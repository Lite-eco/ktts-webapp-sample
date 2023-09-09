/** @jsxImportSource @emotion/react */
import { UpdatePasswordCommandResponse } from '../../generated/command/Commands.generated';
import { appContext } from '../../services/ApplicationContext';
import { MainViewContainer } from '../containers/MainViewContainer';
import { PasswordFormInput } from './components/PasswordForm';
import { UpdatePasswordDialogButton } from './components/UpdatePasswordDialogButton';

export const AccountView = () => {
  const onSubmit = (dto: PasswordFormInput) =>
    appContext.commandService
      .send<UpdatePasswordCommandResponse>({
        objectType: 'UpdatePasswordCommand',
        ...dto
      })
      .then(r => r.result);
  return (
    <MainViewContainer>
      <UpdatePasswordDialogButton onSubmit={onSubmit} />
    </MainViewContainer>
  );
};
