import { Query, QueryResponse } from 'generated/query/Queries.generated';
import { appContext } from 'services/ApplicationContext';

export class QueryService {
  public send = <R extends QueryResponse>(query: Query): Promise<R> =>
    appContext.httpService.get('/query', query).then(r => r.body);
}
