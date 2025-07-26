import {CommentContactInfo} from './commentContactInfo';

export enum FilterContactInfo {
  ALL = 'ALL',
  NOT_REPLIED = 'NOT_REPLIED',
  REPLIED = 'REPLIED'
}

export class ContactInfo {
  id?: number | null;
  status?: FilterContactInfo | null;
  comment?: CommentContactInfo[] | null;

  name: string;
  email: string;
  subject: string;
  message: string;
  accountId: number;

  constructor({
                name,
                email,
                subject,
                message,
                id = null,
                status = null,
                comment = null,
                accountId = null
              }: {
    name: string;
    email: string;
    subject: string;
    message: string;
    id?: number | null;
    status?: FilterContactInfo | null;
    comment?: CommentContactInfo[] | null;
    accountId?: number | null;
  }) {
    this.name = name;
    this.email = email;
    this.subject = subject;
    this.message = message;
    this.id = id;
    this.status = status;
    this.comment = comment;
    this.accountId = accountId;
  }
}
