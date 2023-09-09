/** @jsxImportSource @emotion/react */
import { ControlledPasswordInput } from '../../../common-components/form/ControlledPasswordInput';
import { ClientUid } from '../../../domain/client-ids';
import { PlainStringPassword } from '../../../generated/domain/Security.generated';
import { useI18n } from '../../../hooks/i18n';
import { asNominalString } from '../../../utils/nominal-class';
import { PasswordFormI18n } from './PasswordForm.i18n';
import { css } from '@emotion/react';
import { useForm } from 'react-hook-form';

export interface PasswordFormInput {
  currentPassword: PlainStringPassword;
  newPassword: PlainStringPassword;
}

export interface PasswordFormRawInput {
  currentPassword: string;
  newPassword: string;
}

export const PasswordForm = (props: {
  formId: ClientUid;
  onSubmit: (dto: PasswordFormInput) => Promise<void>;
}) => {
  const {
    handleSubmit,
    control,
    formState: { errors }
  } = useForm<PasswordFormRawInput>();
  const t = useI18n(PasswordFormI18n);
  return (
    <form
      id={props.formId}
      onSubmit={handleSubmit(input =>
        props.onSubmit({
          currentPassword: asNominalString(input.currentPassword),
          newPassword: asNominalString(input.newPassword)
        })
      )}
    >
      <div
        css={css`
          margin: 10px 0;
        `}
      >
        <ControlledPasswordInput
          name="currentPassword"
          label={t.CurrentPassword()}
          control={control}
          errors={errors}
        />
      </div>
      <div
        css={css`
          margin: 10px 0;
        `}
      >
        <ControlledPasswordInput
          name="newPassword"
          label={t.NewPassword()}
          control={control}
          errors={errors}
        />
      </div>
    </form>
  );
};
