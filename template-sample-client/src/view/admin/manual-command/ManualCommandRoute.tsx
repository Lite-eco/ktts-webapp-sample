/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import {
  AdminUpdateSessionsCommand,
  AdminUpdateUserMailCommand,
  CommandResponse
} from 'generated/command/Commands.generated';
import { RequestError } from 'generated/error/Exceptions.generated';
import { useI18n } from 'hooks/i18n';
import { useSnackbar } from 'notistack';
import { useRef, useState } from 'react';
import { appContext } from 'services/ApplicationContext';
import { nominal } from 'utils/nominal-class';
import { ManualCommandRouteI18n } from 'view/admin/manual-command/ManualCommandRoute.i18n';
import { colors } from 'style/vars';
import { RequestErrorId } from 'generated/domain/Ids.generated';

const sampleAdminUpdateSessionsCommand: AdminUpdateSessionsCommand = {
  objectType: 'AdminUpdateSessionsCommand'
};

const sampleAdminUpdateUserMailCommand: AdminUpdateUserMailCommand = {
  objectType: 'AdminUpdateUserMailCommand',
  userId: nominal('92a30f9b8dd44291b88ba6be0afca4ae'),
  mail: 'example@mail.com'
};

export const ManualCommandRoute = () => {
  const commandTextArea = useRef<HTMLTextAreaElement | null>(null);
  const [previousSubmitedValue, setPreviousSubmitedValue] = useState<string>();
  const [okCommandCount, setOkCommandCount] = useState<number>();
  const [totalCommandCount, setTotalCommandCount] = useState<number>();
  const [commandResults, setCommandResults] = useState<any[]>([]);
  const [commandErrorIds, setCommandErrorIds] = useState<RequestErrorId[]>([]);
  const { enqueueSnackbar } = useSnackbar();
  const handleCommand = () => {
    let okCommands = 0;
    setOkCommandCount(okCommands);
    let results: any[] = [];
    setCommandResults(results);
    let errorIds: RequestErrorId[] = [];
    setCommandErrorIds(errorIds);
    setTotalCommandCount(undefined);
    const textCommand = commandTextArea.current?.value;
    if (!textCommand) {
      enqueueSnackbar(t.NoCommand(), {
        variant: 'error'
      });
      return;
    }
    if (textCommand === previousSubmitedValue) {
      enqueueSnackbar(t.CommandAlreadySent(), {
        variant: 'error'
      });
      return;
    }
    let command;
    try {
      command = JSON.parse(textCommand);
    } catch (e) {
      enqueueSnackbar(t.InvalidJson(), {
        variant: 'error'
      });
      return;
    }
    const commands = [];
    if (!Array.isArray(command)) {
      commands.push(command);
    } else {
      command.forEach(c => commands.push(c));
    }
    setPreviousSubmitedValue(textCommand);
    setTotalCommandCount(commands.length);
    // commands.forEach will send all commands at the same time
    commands.forEach(async c => {
      await appContext.httpService
        .post('/command', c)
        .then(r => {
          okCommands++;
          setOkCommandCount(okCommands);
          results = [...results, r.body];
          setCommandResults(results);
        })
        .catch((e: RequestError) => {
          errorIds = [...errorIds, e.id];
          setCommandErrorIds(errorIds);
          enqueueSnackbar(t.ServerError() + e.id, {
            variant: 'error'
          });
        });
    });
  };
  const t = useI18n(ManualCommandRouteI18n);
  return (
    <div
      css={css`
        display: flex;
        gap: 8px;

        pre {
          padding: 10px;
          color: #333;
          word-break: break-all;
          word-wrap: break-word;
          background-color: #f5f5f5;
          border: 1px solid #ccc;
          border-radius: 4px;
        }

        h3 {
          font-size: 1.1rem;
        }
      `}
    >
      <div
        css={css`
          flex: 1;
        `}
      >
        <h1>{t.ManualCommands()}</h1>
        <p>{t.HowTo()}</p>
        <Sample json={sampleAdminUpdateSessionsCommand} />
        <p>{t.CommandsCanBeSentInGroup()}</p>
        <pre>[command1, command2]</pre>
        <h2>{t.Documentation()}</h2>
        <h3>{t.UpdateSessions()}</h3>
        <Sample json={sampleAdminUpdateSessionsCommand} />
        <h3>{t.UpdateUserMail()}</h3>
        <Sample json={sampleAdminUpdateUserMailCommand} />
      </div>
      <div
        css={css`
          flex: 2;
        `}
      >
        <textarea
          ref={commandTextArea}
          rows={40}
          css={css`
            width: 100%;
          `}
        />
        <br />
        <button onClick={handleCommand}>{t.SendCommand()}</button>
        {okCommandCount !== undefined && totalCommandCount !== undefined && (
          <p
            css={css`
              font-weight: bold;
            `}
          >
            {okCommandCount} / {totalCommandCount} {t.ok()}
          </p>
        )}
        {commandResults.map((r, i) => (
          <p key={i}>
            <h3>
              <DocResponse response={r} />
            </h3>
            <Sample json={r} />
          </p>
        ))}
        {commandErrorIds.map((id, i) => (
          <h3
            key={i}
            css={css`
              color: ${colors.errorRed};
            `}
          >
            Error id : <b>{id}</b>
          </h3>
        ))}
      </div>
    </div>
  );
};

// @ts-ignore
const Sample = (props: { json: any }) => (
  <pre>{JSON.stringify(props.json, null, 2)}</pre>
);

/** Should explain data in the response */
const DocResponse = (props: { response: CommandResponse }) => {
  const objectType = props.response.objectType;
  const t = useI18n(ManualCommandRouteI18n);
  switch (objectType) {
    default:
      return t.Result();
  }
};
