/** @jsxImportSource @emotion/react */
import {
  AdminUpdateSessions,
  CommandResponse
} from '../../../generated/command/Commands.generated';
import { RequestError } from '../../../generated/error/Exceptions.generated';
import { useI18n } from '../../../hooks/i18n';
import { appContext } from '../../../services/ApplicationContext';
import { ManualCommandRouteI18n } from './ManualCommandRoute.i18n';
import { css } from '@emotion/react';
import { useSnackbar } from 'notistack';
import { useRef, useState } from 'react';

const sampleAdminUpdateSessions: AdminUpdateSessions = {
  objectType: 'AdminUpdateSessions'
};

/** Should explain data in the response */
const DocResponse = (props: { response: CommandResponse }) => {
  const objectType = props.response.objectType;
  const t = useI18n(ManualCommandRouteI18n);
  switch (objectType) {
    default:
      return t.Result();
  }
};

export const ManualCommandRoute = () => {
  const commandTextArea = useRef<HTMLTextAreaElement | null>(null);
  const [previousSubmitedValue, setPreviousSubmitedValue] = useState<string>();
  const [okCommandCount, setOkCommandCount] = useState<number>();
  const [totalCommandCount, setTotalCommandCount] = useState<number>();
  const [commandResults, setCommandResults] = useState<any[]>([]);
  const { enqueueSnackbar } = useSnackbar();
  const handleCommand = async () => {
    const textCommand = commandTextArea.current?.value;
    if (!textCommand) {
      enqueueSnackbar(t.NoCommand(), {
        variant: 'error'
      });
      return;
    }
    if (textCommand === previousSubmitedValue) {
      enqueueSnackbar(t.CommandAlreadyHandled(), {
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
    let okCommands = 0;
    setOkCommandCount(okCommands);
    let results: any[] = [];
    setCommandResults(results);
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
        .catch((e: RequestError) =>
          enqueueSnackbar(t.ServerError() + e.id, {
            variant: 'error'
          })
        );
    });
  };
  const t = useI18n(ManualCommandRouteI18n);
  return (
    <div
      css={css`
        display: flex;

        & > div {
          margin: 0 5px;
        }

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
        <pre>{JSON.stringify(sampleAdminUpdateSessions, null, 2)}</pre>
        <p>{t.CommandsCanBeSentInGroup()}</p>
        <pre>[command1, command2]</pre>
        <h2>{t.Documentation()}</h2>
        <h3>{t.UpdateSessions()}</h3>
        <pre>{JSON.stringify(sampleAdminUpdateSessions, null, 2)}</pre>
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
            <pre>{JSON.stringify(r, null, 2)}</pre>
          </p>
        ))}
      </div>
    </div>
  );
};
