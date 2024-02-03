/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { ControlledPasswordInput } from 'view/components/form/ControlledPasswordInput';
import { ClientUid } from 'domain/client-ids';
import { PlainStringPassword } from 'generated/domain/Security.generated';
import { useI18n } from 'hooks/i18n';
import { useForm } from 'react-hook-form';
import { nominal } from 'utils/nominal-class';
import { UpdatePasswordFormI18n } from 'view/account/components/UpdatePasswordForm.i18n';

export interface PasswordFormInput {
  currentPassword: PlainStringPassword;
  newPassword: PlainStringPassword;
}

interface PasswordFormRawInput {
  currentPassword: string;
  newPassword: string;
}

export const UpdatePasswordForm = (props: {
  formId: ClientUid;
  onSubmit: (dto: PasswordFormInput) => Promise<void>;
}) => {
  const {
    handleSubmit,
    control,
    formState: { errors }
  } = useForm<PasswordFormRawInput>();
  const t = useI18n(UpdatePasswordFormI18n);
  return (
    <form
      id={props.formId}
      onSubmit={handleSubmit(input =>
        props.onSubmit({
          currentPassword: nominal(input.currentPassword),
          newPassword: nominal(input.newPassword)
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
