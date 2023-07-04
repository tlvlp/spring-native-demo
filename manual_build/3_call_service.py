import asyncio
import aiohttp

url = 'http://localhost:8989/append'
num_requests = 2000


async def fetch_data(session, counter):
    async with session.post(url,
                            headers={'content-type': 'application/json'},
                            json={'appendWith': counter}
                            ) as response:
        await response.json()
        return counter


async def send_requests():
    async with aiohttp.ClientSession() as session:
        tasks = []
        for counter in range(1, num_requests + 1):
            task = asyncio.ensure_future(fetch_data(session, counter))
            tasks.append(task)

        results = await asyncio.gather(*tasks)
        return results


async def main():
    results = await send_requests()
    print(results)

if __name__ == '__main__':
    loop = asyncio.get_event_loop()
    loop.run_until_complete(main())
