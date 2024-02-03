/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { LoadingStatusButton } from 'view/components/LoadingButton';
import { ControlledTextInput } from 'view/components/form/ControlledTextInput';
import { useI18n } from 'hooks/i18n';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { LoadingStatus } from 'interfaces';
import { LostPasswordMailFormI18n } from 'view/lost-password/components/LostPasswordMailFormI18n';

export interface LostPasswordFormMailInput {
  mail: string;
}

export const LostPasswordMailForm = (props: {
  onSubmit: (dto: LostPasswordFormMailInput) => Promise<void>;
}) => {
  const {
    handleSubmit,
    control,
    formState: { errors }
  } = useForm<LostPasswordFormMailInput>();
  const [loading, setLoading] = useState<LoadingStatus>('Idle');
  const t = useI18n(LostPasswordMailFormI18n);
  return (
    <form
      onSubmit={handleSubmit(input => {
        setLoading('Loading');
        props
          .onSubmit({
            mail: input.mail
          })
          .then(() => setLoading('Idle'))
          .catch(() => setLoading('Error'));
      })}
    >
      <div
        css={css`
          margin: 10px 0;
        `}
      >
        <ControlledTextInput
          name="mail"
          label={t.Email()}
          control={control}
          errors={errors}
        />
      </div>
      <LoadingStatusButton loadingStatus={loading} type="submit">
        {t.SendMail()}
      </LoadingStatusButton>
    </form>
  );
};
