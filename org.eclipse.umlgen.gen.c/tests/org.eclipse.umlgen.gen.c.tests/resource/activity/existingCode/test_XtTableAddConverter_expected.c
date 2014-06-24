register ConverterPtr       *pp;
register ConverterPtr       p;
XtConvertArgList args;
pp= &table[ProcHash(from_type, to_type) & CONVERTHASHMASK];
while((p = *pp) && (p->from != from_type || p->to != to_type)) {
	pp = &p->next;
}
if (p) {
	*pp = p->next;
	XtFree((char *)p);
}
p = (ConverterPtr) __XtMalloc(sizeof(ConverterRec) +
                                sizeof(XtConvertArgRec) * num_args);
p->next         = *pp;
*pp = p;
p->from         = from_type;
p->to           = to_type;
p->converter    = converter;
p->destructor   = destructor;
p->num_args     = num_args;
p->global       = global;
args = ConvertArgs(p);
while(num_args--) {
	*args++ = *convert_args++;
}
p->new_style    = new_style;
p->do_ref_count = False;
if (destructor || (cache_type & 0xff)) {
	p->cache_type = cache_type & 0xff;
	if (cache_type & XtCacheRefCount) {
		p->do_ref_count = True;
	}
} else { 
	p->cache_type = XtCacheNone;
}