import { TextField } from '@mui/material';
import { ControlledTextInputI18n } from 'view/components/form/ControlledTextInput.i18n';
import { useI18n } from 'hooks/i18n';
import { ChangeEvent } from 'react';
import { Controller, FieldPath } from 'react-hook-form';
import { Control, FieldValues } from 'react-hook-form/dist/types';
import { FieldErrors } from 'react-hook-form/dist/types/errors';

// TODO[tmpl] from former "manual" inputs :
// const [value, setValue] = useState(props.initialValue ?? ''); // ?? '' is needed for type=date
// don't forget useEffect(setValue,[value])
// what happends with react-hook-form ?
export const ControlledTextInput = <
  TFieldValues extends FieldValues,
  TName extends FieldPath<TFieldValues>
>(props: {
  name: TName;
  label: string;
  control: Control<TFieldValues>;
  errors: FieldErrors<TFieldValues>;
  hideErrorMessage?: boolean;
  autoFocus?: boolean;
  type?: React.InputHTMLAttributes<unknown>['type'];
  onChange?: (
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => void;
  multiline?: boolean;
  multilineDefaultRows?: number;
}) => {
  // label always on top if date (if not, mask & label collide)
  const shrinkLabel = props.type === 'date';
  if (!props.multiline && props.multilineDefaultRows) {
    throw Error();
  }
  const t = useI18n(ControlledTextInputI18n);
  return (
    <Controller
      name={props.name}
      control={props.control}
      rules={{ required: true }}
      render={({ field }) => (
        <TextField
          variant="outlined"
          fullWidth={true}
          {...field}
          onChange={e => {
            field.onChange(e);
            // TODO[tmpl] version onChange on form via react-hook-form ?
            if (props.onChange) {
              props.onChange(e);
            }
          }}
          label={props.label}
          error={!!props.errors[props.name]}
          size="small"
          type={props.type}
          autoFocus={props.autoFocus}
          helperText={
            props.errors[props.name] && !props.hideErrorMessage
              ? t.MandatoryField()
              : undefined
          }
          InputLabelProps={shrinkLabel ? { shrink: true } : undefined}
          multiline={props.multiline ?? false}
          rows={props.multilineDefaultRows}
        />
      )}
    />
  );
};
