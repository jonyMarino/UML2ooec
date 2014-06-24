self->buffer = buffer;
self->len = len;
self->curpos = 0;
self->base.vtbl = &kGioMemoryFileVtbl;
return 1;