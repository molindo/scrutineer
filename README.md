Analyses a secondary stream of information against a known point-of-truth and reports inconsistencies.

The Why
=======

When you have a Lucene-based index of substantial size, say many hundreds of millions of records, what you want is confidence
that your index is correct. In many cases, people use Solr/Elasticsearch/Compass to index their central database, mongodb,
hbase etc so the index is a secondary storage of data.

How do you know if your index is accurate? Can you just reindex 500 million documents anytime you like? (That's the Aliens: "Nuke the site from
Orbit... It's the only way to be sure" approach). No, if there _ARE_ inconsistencies in your index, then you want to:

* find the items that are incorrect (and only them)
* do it fast

Scrutineer has been designed with this in mind, it can find any inconsistencies in your index fast.


How does this work?
===================

Scrutineer relies on your data having 2 core properties:

* an ID - a unique identifier for your object
* a Version - something stored in your primary datastore for that object that represents the temporal state of that object

The Version property is commonly used in an Optimistic Locking pattern. If you store the ID & Version information in your
secondary store (say, Solr/Elasticsearch) then you can always compare for any given item whether the version in secondary store is up
to date.

Scrutineer takes a stream from your primary, and a stream from your secondary store, presumes they are sorted identically (more
on that later) and walks the streams doing a merge comparison. It detects 4 states:

1. Both items are identical (yay!)
2. An ID is missing from the secondary stream (A missed add?  maybe that index message you sent to Solr/Elasticsearch never made it, anyway, it's not there)
3. An ID was detected in the secondary, but wasn't in the primary stream (A missed delete?  something was deleted on the primary, but the secondary never got the memo)
4. The ID exists in both streams, but the Version values are inconsistent (A missed update?  similar to the missed add, this time perhaps an update to a row in your DB never made it to Solr/Elasticsearch)

Example
=======
Here's an example, 2 streams in sorted order, one from the Database (your point-of-truth), 
and one from Elasticsearch (the one you're checking) with the <ID>:<VERSION> for each side:

<table border="1">
  <tr><th>Database</th><th>Elasticsearch</th></tr>
  <tr><td>1:12345</td><td>1:12345</td></tr>
  <tr><td>2:23455</td><td>3:84757</td></tr>
  <tr><td>3:84757</td><td>4:98765</td></tr>
  <tr><td>4:98765</td><td>5:38475</td></tr>
  <tr><td>6:34666</td><td>6:34556</td></tr>
</table>

Scrutineer picks up that:

* ID '2' is missing from Elasticsearch
* ID '5' was deleted from the database at some point, but Elasticsearch still has it in there
* ID '6' is visible in Elasticsearch but appears to have the wrong version
